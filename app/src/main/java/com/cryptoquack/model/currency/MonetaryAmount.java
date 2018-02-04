package com.cryptoquack.model.currency;

import android.support.annotation.NonNull;

/**
 * Created by Duke on 1/24/2018.
 */

public class MonetaryAmount implements Comparable<MonetaryAmount> {

    private double amount;
    private Currencies.Currency currency;

    public MonetaryAmount(double amount, Currencies.Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(Currencies.Currency currency) {
        this.currency = currency;
    }

    public double getAmount(){
        return this.amount;
    }

    public Currencies.Currency getCurrency() {
        return this.currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MonetaryAmount) {
            MonetaryAmount otherAmount = (MonetaryAmount) o;
            return this.amount == otherAmount.amount && this.currency == otherAmount.currency;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {

        if (this.currency == Currencies.Currency.USD) {
            return String.format("$%f", this.amount);
        } else {
            return String.format("$%f", this.amount, this.currency);
        }
    }

    @Override
    public int compareTo(@NonNull MonetaryAmount o) {
        if (this.currency != o.currency) {
            throw new IllegalArgumentException("Cannot compare monetary amount of different currencies.");
        } else {
            if (this.amount == o.amount) {
                return 0;
            } else if (this.amount > o.amount) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
