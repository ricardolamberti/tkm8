

/**
 * Define the version of the Google Pay API referenced when creating your
 * configuration
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#PaymentDataRequest|apiVersion in PaymentDataRequest}
 */
const baseRequest = {
  apiVersion: 2,
  apiVersionMinor: 0
};

/**
 * Card networks supported by your site and your gateway
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#CardParameters|CardParameters}
 * @todo confirm card networks supported by your site and gateway
 */
const allowedCardNetworks = ["AMEX", "DISCOVER", "INTERAC", "JCB", "MASTERCARD", "VISA"];

/**
 * Card authentication methods supported by your site and your gateway
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#CardParameters|CardParameters}
 * @todo confirm your processor supports Android device tokens for your
 * supported card networks
 */
const allowedCardAuthMethods = ["PAN_ONLY", "CRYPTOGRAM_3DS"];

/**
 * Identify your gateway and your site's gateway merchant identifier
 *
 * The Google Pay API response will return an encrypted payment method capable
 * of being charged by a supported gateway after payer authorization
 *
 * @todo check with your gateway on the parameters to pass
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#gateway|PaymentMethodTokenizationSpecification}
 */

const tokenizationSpecification = {
  type: 'PAYMENT_GATEWAY',
  parameters: {
    'gateway': googlepayGateway,
    'gatewayMerchantId': googlepayGatewayMerchantId
  }
};
/**
 * Describe your site's support for the CARD payment method and its required
 * fields
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#CardParameters|CardParameters}
 */
const baseCardPaymentMethod = {
  type: 'CARD',
  parameters: {
    allowedAuthMethods: allowedCardAuthMethods,
    allowedCardNetworks: allowedCardNetworks
  }
};

/**
 * Describe your site's support for the CARD payment method including optional
 * fields
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#CardParameters|CardParameters}
 */
const cardPaymentMethod = Object.assign(
  {},
  baseCardPaymentMethod,
  {
    tokenizationSpecification: tokenizationSpecification
  }
);

/**
 * An initialized google.payments.api.PaymentsClient object or null if not yet set
 *
 * @see {@link getGooglePaymentsClient}
 */
let paymentsClient = null;

/**
 * Configure your site's support for payment methods supported by the Google Pay
 * API.
 *
 * Each member of allowedPaymentMethods should contain only the required fields,
 * allowing reuse of this base request when determining a viewer's ability
 * to pay and later requesting a supported payment method
 *
 * @returns {object} Google Pay API version, payment methods supported by the site
 */
function getGoogleIsReadyToPayRequest() {
  return Object.assign(
      {},
      baseRequest,
      {
        allowedPaymentMethods: [baseCardPaymentMethod]
      }
  );
}

/**
 * Configure support for the Google Pay API
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#PaymentDataRequest|PaymentDataRequest}
 * @returns {object} PaymentDataRequest fields
 */

/**
 * Return an active PaymentsClient or initialize
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/client#PaymentsClient|PaymentsClient constructor}
 * @returns {google.payments.api.PaymentsClient} Google Pay API client
 */
function getGooglePaymentsClient() {
  if ( paymentsClient === null ) {
    paymentsClient = new google.payments.api.PaymentsClient({environment: googlepayEnvironment});
  }
  return paymentsClient;
}

/**
 * Initialize Google PaymentsClient after Google-hosted JavaScript has loaded
 *
 * Display a Google Pay payment button after confirmation of the viewer's
 * ability to pay.
 */
var loadedgoglepay=false;
function onGooglePayLoaded() {
  const paymentsClient = getGooglePaymentsClient();
  paymentsClient.isReadyToPay(getGoogleIsReadyToPayRequest())
      .then(function(response) {
        if (response.result) {
        	loadedgoglepay=true;
            console.log("GOOGLE PLAY Loaded");
        }
      })
      .catch(function(err) {
        console.log("GOOGLE PLAY "+err);
      });
}

/**
 * Add a Google Pay purchase button alongside an existing checkout button
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#ButtonOptions|Button options}
 * @see {@link https://developers.google.com/pay/api/web/guides/brand-guidelines|Google Pay brand guidelines}
 */

/**
 * Provide Google Pay API with a payment amount, currency, and amount status
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/request-objects#TransactionInfo|TransactionInfo}
 * @returns {object} transaction info, suitable for use as transactionInfo property of PaymentDataRequest
// */

/**
 * Prefetch payment data to improve performance
 *
 * @see {@link https://developers.google.com/pay/api/web/reference/client#prefetchPaymentData|prefetchPaymentData()}
 */


/**
 * Show Google Pay payment sheet when Google Pay payment button is clicked
 */

/**
 * Process payment data returned by the Google Pay API
 *
 * @param {object} paymentData response from Google Pay API after user approves payment
 * @see {@link https://developers.google.com/pay/api/web/reference/response-objects#PaymentData|PaymentData object reference}
 */

paymentManager = function(zcomponentId,zglobalTransacc,zaction) {
    this.globalTransacc=zglobalTransacc;
	this.componentId=zcomponentId+'_button';
	this.componentOut=zcomponentId;
	
	this.action=zaction;
}
paymentManager.prototype.getGoogleTransactionInfo = function () {
    	return this.globalTransacc;
};
paymentManager.prototype.addGooglePayButton = function () {
     	  const paymentsClient = getGooglePaymentsClient();
     	  const that=this;
    	  const button = paymentsClient.createButton({onClick: function(){onGooglePaymentButtonClicked(that);}});
    	  document.getElementById(this.componentId).appendChild(button);
};
paymentManager.prototype.getGooglePaymentDataRequest = function () {
	  const paymentDataRequest = Object.assign({}, baseRequest);
	  paymentDataRequest.allowedPaymentMethods = [cardPaymentMethod];
	  paymentDataRequest.transactionInfo = this.getGoogleTransactionInfo();
	  paymentDataRequest.merchantInfo = {
	    // @todo a merchant ID is available for a production environment after approval by Google
	    // See {@link https://developers.google.com/pay/api/web/guides/test-and-deploy/integration-checklist|Integration checklist}
	    // merchantId: '12345678901234567890',
	    merchantName: 'Example Merchant'
	  };
	  return paymentDataRequest;
	};
	
function onGooglePaymentButtonClicked(that) {
	that.onGooglePaymentButtonClicked();
}

paymentManager.prototype.onGooglePaymentButtonClicked = function () {
	   const paymentDataRequest = this.getGooglePaymentDataRequest();
	   paymentDataRequest.transactionInfo =this.getGoogleTransactionInfo();
	   const that=this;
	   const paymentsClient = getGooglePaymentsClient();
	   paymentsClient.loadPaymentData(paymentDataRequest)
	       .then(function(paymentData) {
	         // handle the response
	         processPayment(that,paymentData);
	       })
	       .catch(function(err) {
     	    $('#'+that.componentOut).val('ERROR: '+err);
      	    that.action();
       });
};

function processPayment(that,paymentData) {
	that.processPayment(paymentData);
}
paymentManager.prototype.processPayment  = function (paymentData) {
	    console.log("GOOGLE PLAY "+paymentData);
  	    paymentToken = paymentData.paymentMethodData.tokenizationData.token;
  	    $('#'+this.componentOut).val(JSON.stringify(paymentData));
  	    this.action();
};


paymentManager.prototype.prefetchGooglePaymentData = function () {
	  const paymentDataRequest = this.getGooglePaymentDataRequest();
	  // transactionInfo must be set but does not affect cache
	  paymentDataRequest.transactionInfo = {
	    totalPriceStatus: 'NOT_CURRENTLY_KNOWN',
	    currencyCode: 'USD'
	  };
	  const paymentsClient = getGooglePaymentsClient();
	  paymentsClient.prefetchPaymentData(paymentDataRequest);
	};

function createPaymentManager(zcomponentId,zglobalTransacc,action) {
	new paymentManager(zcomponentId,zglobalTransacc,action).addGooglePayButton();
	
}



