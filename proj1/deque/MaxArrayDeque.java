package deque;

import java.util.Comparator;

public abstract class MaxArrayDeque<T> extends ArrayDeque {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        if (this.size() == 0) {
            return null;
        }
        T max = (T) this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T val = (T) this.get(i);
            if (comparator.compare(val, max) > 0) {
                max = val;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        T max = (T) this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T val = (T) this.get(i);
            if (c.compare(val, max) > 0) {
                max = val;
            }
        }
        return max;
    }

    public int size() {
        return super.size();
    }

    public T get(int index) {
        return (T) super.get(index);
    }
}
