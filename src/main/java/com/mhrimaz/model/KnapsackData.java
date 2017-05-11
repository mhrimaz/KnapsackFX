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
package com.mhrimaz.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hossein
 */
public class KnapsackData {

    private final List<Item> availableItem;
    private final int maximumWeight;

    public KnapsackData(int maxSize) {
        availableItem = new ArrayList<>();
        this.maximumWeight = maxSize;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public int getSize() {
        return availableItem.size();
    }

    public Item getData(int index) {
        return availableItem.get(index);
    }

    public void addItem(int index, Item item) {
        availableItem.add(index, item);
    }

    public void addItem(Item item) {
        availableItem.add(item);
    }

    public void addItem(List<Item> item) {
        availableItem.addAll(item);
    }

}
