/*
 * The MIT License
 *
 * Copyright 2017 Your Organisation.
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

import com.mhrimaz.model.BinarySolution;
import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.solver.KnapsackSolver;
import com.mhrimaz.solver.strategy.DPStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhrimaz
 */
public class TestDPSolution {

    private KnapsackData knapsackData;

    @Before
    public void setUp() {
        knapsackData = new KnapsackData(50);
        knapsackData.addItem(new Item(10, 60));
        knapsackData.addItem(new Item(20, 100));
        knapsackData.addItem(new Item(30, 120));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDPSolution() {
        KnapsackSolver solver = new KnapsackSolver(knapsackData, new DPStrategy());
        Solution solution = solver.getSolution();
        assertEquals(solution.getGainedValue(), 220);
        assertEquals(solution.getGainedWeight(), 50);
    }

}
