package com.soundcloud.lightcycle;

import javax.lang.model.element.Name;

enum LightCycleDispatcherKind {
    DEFAULT_ACTIVITIES {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("LightCycleAppCompatActivity") || name.contentEquals("LightCycleActionBarActivity");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("ActivityLightCycle", dispatchedType);
        }
    },
    DEFAULT_FRAGMENT {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("LightCycleFragment");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("FragmentLightCycle", dispatchedType);
        }
    },
    DEFAULT_SUPPORT_FRAGMENT {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("LightCycleSupportFragment");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("SupportFragmentLightCycle", dispatchedType);
        }
    },
    DEFAULT_PREFERENCE_FRAGMENT {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("LightCyclePreferenceFragmentCompat");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("SupportFragmentLightCycle", dispatchedType);
        }
    },
    BASE_ACTIVITY_DISPATCHER {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("ActivityLightCycleDispatcher");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("ActivityLightCycle", dispatchedType);
        }
    },
    BASE_SUPPORT_FRAGMENT_DISPATCHER {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("SupportFragmentLightCycleDispatcher");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("SupportFragmentLightCycle", dispatchedType);
        }
    },
    BASE_FRAGMENT_DISPATCHER {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("FragmentLightCycleDispatcher");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return typeArgumentAsString("FragmentLightCycle", dispatchedType);
        }
    },
    DISPATCHER_INTERFACE {
        @Override
        boolean matches(Name name) {
            return name.contentEquals("LightCycleDispatcher");
        }

        @Override
        String toTypeName(String dispatchedType) {
            return dispatchedType;
        }

    };

    private static String typeArgumentAsString(String lightCycleType, String componentType) {
        return String.format("%s.%s<%s>", LightCycleProcessor.LIB_PACKAGE, lightCycleType, componentType);
    }

    abstract boolean matches(Name name);

    abstract String toTypeName(String dispatchedType);
}
