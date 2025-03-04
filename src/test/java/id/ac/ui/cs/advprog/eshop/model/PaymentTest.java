package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankPaymentData;
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.voucherPaymentData = new HashMap<>();
        this.voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        this.bankPaymentData = new HashMap<>();
        this.bankPaymentData.put("bankName", "BCA");
        this.bankPaymentData.put("referenceCode", "REF1234567890123");

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sampo Cap Paes");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentWithoutPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", null, this.order);
        });
    }

    @Test
    void testCreateVoucherPaymentWithInvalidPaymentData() {
        Map<String, String> paymentDataWithoutESHOPPrefix = new HashMap<>();
        Map<String, String> paymentDataWithoutEightNumbers = new HashMap<>();
        Map<String, String> paymentDataWithLessThanSixteenCharacters = new HashMap<>();
        Map<String, String> paymentDataWithGreaterThanSixteenCharacters = new HashMap<>();

        paymentDataWithoutESHOPPrefix.put("voucherCode", "1234ABC5678");
        paymentDataWithoutEightNumbers.put("voucherCode", "ESHOP1234ABC");
        paymentDataWithLessThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC567");
        paymentDataWithGreaterThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC56781234");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", paymentDataWithoutESHOPPrefix, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", paymentDataWithoutEightNumbers, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", paymentDataWithLessThanSixteenCharacters, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", paymentDataWithGreaterThanSixteenCharacters, this.order);
        });
    }

    @Test
    void testCreateBankPaymentWithInvalidData() {
        Map<String, String> paymentDataWithoutBankName = new HashMap<>();
        Map<String, String> paymentDataWithoutReferenceCode = new HashMap<>();
        Map<String, String> paymentDataWithEmptyBankName = new HashMap<>();
        Map<String, String> paymentDataWithEmptyReferenceCode = new HashMap<>();

        paymentDataWithoutBankName.put("referenceCode", "REF1234567890123");
        paymentDataWithoutReferenceCode.put("bankName", "BCA");
        paymentDataWithEmptyBankName.put("bankName", "");
        paymentDataWithEmptyBankName.put("referenceCode", "REF1234567890123");
        paymentDataWithEmptyReferenceCode.put("referenceCode", "");
        paymentDataWithEmptyReferenceCode.put("bankName", "BCA");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithoutBankName, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithoutReferenceCode, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithEmptyBankName, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithEmptyReferenceCode, this.order);
        });
    }

    @Test
    void testCreatePaymentWithoutOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", this.voucherPaymentData, null);
        });
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("MEOW", this.voucherPaymentData, this.order);
        });
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("VOUCHER", this.voucherPaymentData, this.order);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }

    @Test
    void testCreateVoucherPaymentSuccess() {
        Payment payment = new Payment("VOUCHER", this.voucherPaymentData, this.order);
        assertNotNull(payment.getId(), "Payment id should not be null");
        assertEquals("WAITING", payment.getStatus());
        assertSame(this.voucherPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }

    @Test
    void testCreateBankPaymentSuccess() {
        Payment payment = new Payment("BANK", this.bankPaymentData, this.order);
        assertNotNull(payment.getId(), "Payment id should not be null");
        assertEquals("WAITING", payment.getStatus());
        assertSame(this.bankPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }
}