package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Spy
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Order> orders;
    List<Payment> payments;

    Map<String, String> voucherPaymentData;
    Map<String, String> bankPaymentData;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        payments = new ArrayList<>();

        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        bankPaymentData = new HashMap<>();
        bankPaymentData.put("bankName", "BCA");
        bankPaymentData.put("referenceCode", "REF1234567890123");

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products,
                1708560000L, "Safira Sudrajat");
        orders.add(order);

        Payment payment1 = new Payment("VOUCHER", voucherPaymentData, order);
        payments.add(payment1);
        Payment payment2 = new Payment("BANK", bankPaymentData, order);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment1 = payments.get(0);
        doReturn(payment1)
                .when(paymentRepository)
                .addPayment(any(Order.class), any(String.class), any(Map.class));
        payment1 = paymentService.addPayment(payment1.getOrder(), "VOUCHER", payment1.getPaymentData());

        Payment payment2 = payments.get(1);
        doReturn(payment2)
                .when(paymentRepository)
                .addPayment(any(Order.class), any(String.class), any(Map.class));
        payment2 = paymentService.addPayment(payment2.getOrder(), "BANK", payment2.getPaymentData());

        doReturn(payment1).when(paymentRepository).getPayment(payment1.getId());
        Payment findResult = paymentService.getPayment(payment1.getId());
        assertEquals(payment1.getId(), findResult.getId());
        assertEquals(payment1.getMethod(), findResult.getMethod());
        assertEquals(payment1.getStatus(), findResult.getStatus());

        doReturn(payment2).when(paymentRepository).getPayment(payment2.getId());
        findResult = paymentService.getPayment(payment2.getId());
        assertEquals(payment2.getId(), findResult.getId());
        assertEquals(payment2.getMethod(), findResult.getMethod());
        assertEquals(payment2.getStatus(), findResult.getStatus());
    }

    @Test
    void testSetStatusSuccessful() {
        Payment payment1 = new Payment("VOUCHER", voucherPaymentData, orders.get(0));
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
        Payment payment1 = payments.get(0);
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payment1, "MEOW")
        );
    }

    @Test
    void testGetPaymentIfFound() {
        Payment payment1 = payments.get(0);
        doReturn(payment1).when(paymentRepository).getPayment(payment1.getId());
        Payment paymentFound = paymentService.getPayment(payment1.getId());
        assertEquals(payment1.getId(), paymentFound.getId());
        assertEquals("VOUCHER", paymentFound.getMethod());
        assertEquals(payment1.getStatus(), paymentFound.getStatus());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).getPayment("AAA");
        Payment payment = paymentService.getPayment("AAA");
        assertNull(payment);
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();
        List<Payment> paymentList = paymentService.getAllPayments();
        assertSame(payments, paymentList);
    }
}
