package java.com.graph.struct;

import com.graph.struct.BoardStateHeap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryHeapTest {

        BoardStateHeap b = new BoardStateHeap();
        @Test
                public void montest() {
                assertEquals(false, b.isAlreadyEncountered(1), "3 must be in the tree");
                b.insert(1);
                b.insert(2);
                b.insert(3);
                assertEquals(true, b.isAlreadyEncountered(3), "3 must be in the tree");
        }
}