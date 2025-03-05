package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankPaymentData;
    private List<Product> products;
    private Order order1;

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
        this.products.add(product1);

        this.order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreateVoucherPayment() {
        Payment payment = paymentService.addPayment(order1,
                PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        doReturn(payment).when(paymentRepository).addPayment(order1,
                PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        verify(paymentRepository, times(1)).addPayment(order1,
                PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankPayment() {
        Payment payment = paymentService.addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        doReturn(payment).when(paymentRepository).addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        verify(paymentRepository, times(1)).addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherInvalid() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALIDCODE");
        Payment payment = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order1.getStatus());
    }

    @Test
    void testGetAllPayments() {
        paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        paymentService.addPayment(order1, PaymentMethod.BANK.getValue(), bankPaymentData);
        assertEquals(2, paymentService.getAllPayments().size());
    }

    @Test
    void testGetByIdFound() {
        Payment payment = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        assertEquals(payment, paymentService.getPayment(payment.getId()));
    }

    @Test
    void testGetByIdNotFound() {
        doReturn(null).when(paymentRepository).getPayment("invalidId");
        assertNull(paymentService.getPayment("invalidId"));
    }

    @Test
    void testCreateInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment(order1, "OVO", voucherPaymentData));
    }

    @Test
    void testSetStatusSuccessful() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP00000000AAA");
        Payment payment1 = new Payment( "", paymentData, order1);

        assertEquals(PaymentStatus.WAITING.getValue(), payment1.getStatus());
        paymentService.setStatus(payment1, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment1.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), payment1.getOrder().getStatus());

        paymentService.setStatus(payment1, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment1.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), payment1.getOrder().getStatus());
    }

    @Test
    void testSetStatusFail() {
        Payment payment1 = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payment1, "MEOW")
        );
    }
}