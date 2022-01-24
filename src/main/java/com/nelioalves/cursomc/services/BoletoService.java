package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {
    //Somente um regra para acrescentar 7 dias para geração do boleto.
    public void preecherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(instanteDoPedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
    }

}
