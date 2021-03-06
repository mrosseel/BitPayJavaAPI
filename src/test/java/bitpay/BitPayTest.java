package bitpay;

import bitpay.model.BitPay;
import bitpay.model.Invoice;
import bitpay.model.InvoiceParams;
import bitpay.model.Rates;
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class BitPayTest {

	private BitPay bitpay;
	private Invoice basicInvoice;
	private static String API_KEY = "YOUR_API_KEY_HERE";
	private static double BTC_EPSILON = .000000001;
	private static double EPSILON = .001;
	
	@Before
	public void setUp() throws Exception {
		this.bitpay = new BitPay(API_KEY, "USD");
		
		basicInvoice = this.bitpay.createInvoice(100);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShouldCreateInvoice1BTC() {
		this.bitpay = new BitPay(API_KEY, "BTC");
		
		Invoice invoice = this.bitpay.createInvoice(1);
		assertEquals(invoice.getBtcPrice(), 1.0, BTC_EPSILON);
	}
	
	@Test
	public void testShouldCreateInvoice100USD() {
		this.bitpay = new BitPay(API_KEY, "USD");
		
		Invoice invoice = this.bitpay.createInvoice(100);
		assertEquals(invoice.getPrice(), 100.0, EPSILON);
	}
	
	@Test
	public void testShouldGetInvoiceId() {
		assertNotNull(basicInvoice.getId());
	}
	
	@Test
	public void testShouldGetInvoiceURL() {
		assertNotNull(basicInvoice.getUrl());
	}
	
	@Test
	public void testShouldGetInvoiceStatusL() {
		assertNotNull(basicInvoice.getStatus());
	}
	
	@Test
	public void testShouldGetInvoiceBTCPrice() {
		assertNotNull(basicInvoice.getBtcPrice());
	}

	@Test
	public void testShouldCreateInvoice100EUR() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Invoice invoice = this.bitpay.createInvoice(100);
		assertEquals(invoice.getPrice(), 100.0, EPSILON);
	}
	
	@Test
	public void testShouldGetInvoice() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Invoice invoice = this.bitpay.createInvoice(100);

		Invoice retreivedInvoice = this.bitpay.getInvoice(invoice.getId());
		
		assertEquals(invoice.getId(), retreivedInvoice.getId());
	}
	
	@Test
	public void testShouldCreateInvoiceWithAdditionalParams() {
		this.bitpay = new BitPay(API_KEY, "USD");
		
		InvoiceParams params = new InvoiceParams();
		params.setBuyerName("Satoshi");
		params.setBuyerEmail("satoshi@bitpay.com");
		params.setFullNotifications(true);
		params.setNotificationEmail("satoshi@bitpay.com");
		
		Invoice invoice = this.bitpay.createInvoice(100, params);
		
		//Print to verify the information is on the invoice.
		System.out.println(invoice.getUrl());
		
		assertNotNull(invoice);
	}

	
	@Test
	public void testShouldGetExchangeRates() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Rates rates = this.bitpay.getRates();
		
		JSONArray arrayRates = rates.getRates();
		
		System.out.println("Exchange Rates: " + arrayRates);
		
		assertNotNull(arrayRates);
	}
	
	@Test
	public void testShouldGetUSDExchangeRate() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Rates rates = this.bitpay.getRates();
		double rate = rates.getRate("USD");

		assertTrue(rate != 0);
	}
	
	@Test
	public void testShouldGetEURExchangeRate() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Rates rates = this.bitpay.getRates();
		double rate = rates.getRate("EUR");
		
		assertTrue(rate != 0);
	}
	
	@Test
	public void testShouldGetCNYExchangeRate() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Rates rates = this.bitpay.getRates();
		double rate = rates.getRate("CNY");
		
		assertTrue(rate != 0);
	}
	
	@Test
	public void testShouldUpdateExchangeRates() {
		this.bitpay = new BitPay(API_KEY, "EUR");
		
		Rates rates = this.bitpay.getRates();
		
		rates.update();
		
		JSONArray arrayRates = rates.getRates();
		
		assertNotNull(arrayRates);
	}

}
