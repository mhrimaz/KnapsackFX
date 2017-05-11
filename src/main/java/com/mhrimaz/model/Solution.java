package com.mhrimaz.model;
/*
 * The MIT License
 *
 * Copyright 2017 Hossein Rimaz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import java.util.List;

/**
 * Solution class represent binary encode of the problem solution each 1 means
 * we will pick that item corresponding to the array of item.
 *
 * @author hossein
 */
public final class Solution {

    private final List<Item> pickedItem;
    private long gainedValue;
    private long gainedWeight;
    private long takenTime;

    public Solution(List<Item> pickedItem, long takenTime) {
        this.pickedItem = pickedItem;
        gainedValue = -1;
        gainedWeight = -1;
        this.takenTime = takenTime;
    }

    /**
     * @return the pickedItem
     */
    public List<Item> getPickedItem() {
        return pickedItem;
    }

    /**
     * @return the gainedValue
     */
    public long getGainedValue() {
        if (gainedValue == -1) {
            gainedValue = pickedItem.stream().mapToInt(item -> item.getValue()).sum();
        }
        return gainedValue;
    }

    /**
     * @return the gainedWeight
     */
    public long getGainedWeight() {
        if (gainedWeight == -1) {
            gainedWeight = pickedItem.stream().mapToInt(item -> item.getWeight()).sum();
        }
        return gainedWeight;
    }

    /**
     * 
     * @param takenTime taken time in ms
     */
    public void setTakenTime(long takenTime) {
        this.takenTime = takenTime;
    }

    /**
     * @return the takenTime
     */
    public long getTakenTime() {
        return takenTime;
    }

    
}
