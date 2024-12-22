package com.li.hellospring2.service.impl;

import com.li.hellospring2.bean.Account;
import com.li.hellospring2.bean.Book;
import com.li.hellospring2.bean.Order;
import com.li.hellospring2.dao.AccountDao;
import com.li.hellospring2.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AccountServiceImpl {
    @Autowired
    BookDao bookDao;
    @Autowired
    AccountDao accountDao;

    /**
     * check oud method
     *
     * @param order:Order order
     */
    @Transactional
    public boolean checkout(Order order) {
        try {
            BigDecimal totalprice = new BigDecimal(0);
            Account account = accountDao.getAccountById(order.getAcccountId());
            for (Map.Entry<Integer, Integer> entry : order.getOrderMap().entrySet()) {
                Integer bid = entry.getKey();
                Book book = bookDao.getBookById(bid);
                assert book.getStock() >= entry.getValue();
                totalprice = totalprice.add(book.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
                assert account.getBalance().compareTo(totalprice) >= 0;
            }
            accountDao.updateBalance(account.getId(), totalprice.multiply(new BigDecimal(-1)));
            for (Map.Entry<Integer, Integer> entry : order.getOrderMap().entrySet()) {
                bookDao.updateBookStock(entry.getKey(), -entry.getValue());
            }
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;
    }
}
