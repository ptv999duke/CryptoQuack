package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.R;

/**
 * Created by Duke on 2/5/2018.
 */

public class OrderItemView extends LinearLayout {

    private TextView orderTimeTextView;
    private TextView orderActionTextView;
    private TextView orderTypeTextView;
    private TextView orderAmountPriceTextView;
    private TextView orderProgressTextView;
    private Button additionalActionButton;

    public OrderItemView(Context context) {
        super(context);
        this.init();
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_item, this, true);
        this.orderTimeTextView = (TextView) this.findViewById(R.id.order_time_text_view);
        this.orderActionTextView = (TextView) this.findViewById(R.id.order_action_text_view);
        this.orderTypeTextView = (TextView) this.findViewById(R.id.order_type_text_view);
        this.orderAmountPriceTextView = (TextView) this.findViewById(R.id.order_amount_price_text_view);
        this.orderProgressTextView = (TextView) this.findViewById(R.id.order_progress_text_view);
        this.additionalActionButton = (Button) this.findViewById(R.id.order_additional_action_button);
    }
}
