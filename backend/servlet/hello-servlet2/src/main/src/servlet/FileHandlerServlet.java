package servlet;

import bean.UpFile;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@WebServlet(
        name = "filehandler",
        value = "/api/file",
        loadOnStartup = 10
)
@MultipartConfig
public class FileHandlerServlet extends HttpServlet {
    private File tmpDir = null;
    private int __BUFFER_SIZE__ = 64 * 1024;// 64KB

    public void init() {
        tmpDir = new File(this.getServletContext().getRealPath("/"), "tmp");
        if (!tmpDir.exists()) tmpDir.mkdirs();
        else assert (tmpDir.isDirectory());
        System.out.println("[temp directory] " + tmpDir.getAbsolutePath());
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //    private File
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            // CORS
            resp.setHeader("Access-Control-Allow-Origin", "*");  // Allow any origin
            resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");  // Allow HTTP methods
            resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            String path = req.getParameter("path") != null && !req.getParameter("path").equals("") ? req.getParameter("path") : "/";
            File targetPath = new File(tmpDir, path);
            if (targetPath.isFile())
                throw new IOException("target directory is a file");
            else if (!targetPath.isDirectory())
                targetPath.mkdirs();
            boolean overwrite = Boolean.parseBoolean(req.getParameter("overwrite"));
            Part part = req.getPart("upfile");
            String fileName = part.getSubmittedFileName();
            File file = new File(targetPath, fileName);
            if (file.isDirectory())
                throw new IOException("File is a directory");
            else if (file.isFile() && !overwrite)
                throw new IOException("File already exists and not to be overwrite");
            part.write(file.getAbsolutePath());
            resp.getWriter().write("{\"msg\": \"success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"msg\": \"" + e.getMessage() + "\"}");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // get file list under tmpDir
        int limit = 10;
        int page = 1;
        String path = "/";
        resp.setContentType("application/json");
        // CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        try {
            path = req.getParameter("path") != null && !req.getParameter("path").equals("") ? req.getParameter("path") : "/";
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"msg\": \"bad param\"}");
            return;
        }
        try {
            File targetPath = new File(this.tmpDir, path);
            System.out.println(targetPath.getAbsolutePath());
            if (!targetPath.exists()) {
                resp.getWriter().write("{\"file\": [], \"path\": \"" + path + "\"}");
            }else if(! targetPath.getAbsolutePath().startsWith(tmpDir.getAbsolutePath())){
                resp.setStatus(403);
                resp.getWriter().write("{\"file\": [], \"path\": \"" + path + "\"}");
            }else if (targetPath.isFile()) {
                // file download
                // set mime type for response
                String mimeType = req.getServletContext().getMimeType(targetPath.getName());
                resp.setContentType(mimeType);
                resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(targetPath.getName(), "utf-8"));
                //设置上下文域
                ServletContext application = getServletContext();
                try (InputStream input = Files.newInputStream(targetPath.toPath())) {
                    try (ServletOutputStream output = resp.getOutputStream()) {
                        byte[] cache = new byte[this.__BUFFER_SIZE__];
                        for (int len = input.read(cache); len != -1; len = input.read(cache)) {
                            output.write(cache, 0, len);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.setContentType("application/json");
                    resp.getOutputStream().close();
                    resp.getWriter().write("{\"msg\": \"" + e.getMessage() + "\"}");
                    return;
                }
            } else {
                // ls directory
                try {
                    limit = Integer.parseInt(req.getParameter("limit"));
                    page = Integer.parseInt(req.getParameter("page"));
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.getWriter().write("{\"msg\": \"bad param\"}");
                    return;
                }
                File[] files = targetPath.listFiles();
                List<UpFile> UpFiles = new LinkedList<UpFile>();
                for (int i = limit * (page - 1); i < files.length && i < limit * page; i++)
                    UpFiles.add(new UpFile(files[i]));
                resp.getWriter().write("{\"file\": " + JSON.toJSONString(UpFiles) + ", \"path\": \"" + path + "\"}");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"msg\": \"" + e.getMessage() + "\"}");
        }
    }
}
