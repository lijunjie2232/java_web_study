package servlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "asynctest", value = "/asynctest", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(String.format("[Main Thread: %s]: doget", Thread.currentThread().getName()));
        AsyncContext ac = req.startAsync(req, resp);
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println(String.format("[Thread: %s]: complete", Thread.currentThread().getName()));
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println(String.format("[Thread: %s]: start", Thread.currentThread().getName()));
            }
        });
        ac.start(() -> {
            System.out.println(String.format("[Thread: %s]: run", Thread.currentThread().getName()));
            try {
                PrintWriter writer = ac.getResponse().getWriter();
                writer.write("async ok");
                writer.flush();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("[Thread: %s]: finish", Thread.currentThread().getName()));
            ac.complete();
        });
        System.out.println(String.format("[Main Thread: %s]: finish", Thread.currentThread().getName()));
    }
}
