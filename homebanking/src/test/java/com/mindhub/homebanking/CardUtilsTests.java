package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberType(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,isA(String.class));
    }

    @Test
    public void maximumNumberAllowedOfCVV(){
        int CVV = CardUtils.getCVV();
        assertThat(CVV,lessThan(999));
    }

    @Test
    public void CVVNumberType(){
        int cvv = CardUtils.getCVV();
        assertThat(cvv, instanceOf(int.class));
    }
}
