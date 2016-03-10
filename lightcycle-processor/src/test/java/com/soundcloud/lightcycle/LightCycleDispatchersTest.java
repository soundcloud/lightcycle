package com.soundcloud.lightcycle;

import javax.lang.model.element.Name;

public class LightCycleDispatchersTest {


    private static class NameStub implements Name {
        private final String name;

        private NameStub(String name) {
            this.name = name;
        }

        @Override
        public boolean contentEquals(CharSequence cs) {
            return cs.toString().equals(name);
        }

        @Override
        public int length() {
            return name.length();
        }

        @Override
        public char charAt(int index) {
            return name.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return name.subSequence(start, end);
        }
    }
}
