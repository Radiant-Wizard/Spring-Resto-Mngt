package com.Radiant_wizard.GastroManagementApp.Service.Sale;

import com.Radiant_wizard.GastroManagementApp.entity.model.Sale;
import com.Radiant_wizard.GastroManagementApp.repository.sale.SaleDaoImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService{
    private final SaleDaoImpl saleDao;

    public SaleServiceImpl(SaleDaoImpl saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public List<Sale> getSale(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<Sale> sales = saleDao.getSale(startTime, endTime);
            if (sales.isEmpty()){
                throw new RuntimeException("not found");
            } else {
                System.out.println(sales);
                return sales;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
