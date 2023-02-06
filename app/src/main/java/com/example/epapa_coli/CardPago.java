package com.example.epapa_coli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;

import com.paymentez.android.Paymentez;
import com.paymentez.android.model.Card;
import com.paymentez.android.rest.TokenCallback;
import com.paymentez.android.rest.model.PaymentezError;
import com.paymentez.android.view.CardMultilineWidget;

public class CardPago extends AppCompatActivity {

    //Is Paymentez SDK DEV environment?
    public static boolean PAYMENTEZ_IS_TEST_MODE = true;

    //Ask the Paymentez team for it
    public static String PAYMENTEZ_CLIENT_APP_CODE = "NUVEISTG-EC-CLIENT";

    //Ask the Paymentez team for it.
    public static String PAYMENTEZ_CLIENT_APP_KEY = "rvpKAv2tc49x6YL38fvtv5jJxRRiPs";

    //Backend Deployed from https://github.com/paymentez/example-java-backend
    public static String BACKEND_URL = "https://example-paymentez-backend.herokuapp.com";

    Button buttonNext;
    CardMultilineWidget cardWidget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pago);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Paymentez.setEnvironment(PAYMENTEZ_IS_TEST_MODE, PAYMENTEZ_CLIENT_APP_CODE, PAYMENTEZ_CLIENT_APP_KEY);

        cardWidget = (CardMultilineWidget) findViewById(R.id.card_multiline_widget);
        buttonNext = (Button) findViewById(R.id.buttonAddCard);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonNext.setEnabled(false);

                Card cardToSave = cardWidget.getCard();
                if (cardToSave == null) {
                    buttonNext.setEnabled(true);
                    Alert.show(CardPago.this,
                            "Error",
                            "Invalid Card Data");
                    return;
                } else {
                    final ProgressDialog pd = new ProgressDialog(CardPago.this);
                    pd.setMessage("");
                    pd.show();

                    Paymentez.addCard(getApplicationContext(), "1", "josecarrasco1998@outlook.com", cardToSave, new TokenCallback() {

                        public void onSuccess(Card card) {
                            buttonNext.setEnabled(true);
                            pd.dismiss();
                            if (card != null) {
                                if (card.getStatus().equals("valid")) {
                                    Alert.show(getApplicationContext(),
                                            "Card Successfully Added",
                                            "status: " + card.getStatus() + "\n" +
                                                    "Card Token: " + card.getToken() + "\n" +
                                                    "transaction_reference: " + card.getTransactionReference());

                                } else if (card.getStatus().equals("review")) {
                                    Alert.show(getApplicationContext(),
                                            "Card Under Review",
                                            "status: " + card.getStatus() + "\n" +
                                                    "Card Token: " + card.getToken() + "\n" +
                                                    "transaction_reference: " + card.getTransactionReference());

                                } else {
                                    Alert.show(getApplicationContext(),
                                            "Error",
                                            "status: " + card.getStatus() + "\n" +
                                                    "message: " + card.getMessage());
                                }


                            }

                            //TODO: Create charge or Save Token to your backend
                        }

                        public void onError(PaymentezError error) {
                            buttonNext.setEnabled(true);
                            pd.dismiss();
                            Alert.show(getApplicationContext(),
                                    "Error",
                                    "Type: " + error.getType() + "\n" +
                                            "Help: " + error.getHelp() + "\n" +
                                            "Description: " + error.getDescription());

                            //TODO: Handle error
                        }

                    });

                }
            }
        });



    }
}