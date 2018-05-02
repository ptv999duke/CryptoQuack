package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderListPresenter;
import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 4/15/2018.
 */

public class OrderItemsRecyclerAdapter extends
        RecyclerView.Adapter<OrderItemsRecyclerAdapter.OrderItemViewHolder> {

    private final IOrderListPresenter presenter;
    private Context context;
    private OrderItemClickListener callBack;

    public OrderItemsRecyclerAdapter(IOrderListPresenter presenter, Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    public interface OrderItemClickListener {
        public abstract void onOrderItemClick(Order order);
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderItemView orderItemView = new OrderItemView(this.context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        orderItemView.setLayoutParams(lp);
        orderItemView.Init();
        OrderItemViewHolder vh = new OrderItemViewHolder(orderItemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, final int position) {
        Order order = this.presenter.getOrderAtPosition(position);
        holder.orderItemView.setOrder(order);
        holder.orderItemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.onOrderClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.presenter.getOrdersCount();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        private OrderItemView orderItemView;

        public OrderItemViewHolder(OrderItemView orderItemView) {
            super(orderItemView);
            this.orderItemView = orderItemView;
        }
    }
}
