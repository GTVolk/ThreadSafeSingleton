package ru.devvault;

/**
 * Static Field Singleton
 * + Простая и прозрачная реализация
 * + Потокобезопасность
 * - Не ленивая инициализация
 */
class SFSingleton {
    public static final SFSingleton INSTANCE = new SFSingleton();

    public String someMethod() {
        return this.getClass() + " says hello!";
    }
}

/**
 * Enum Singleton
 * + Остроумно
 * + Сериализация из коробки
 * + Потокобезопасность из коробки
 * + Возможность использования EnumSet, EnumMap и т.д.
 * + Поддержка switch
 * - Не ленивая инициализация
 */
enum ESingleton {
    INSTANCE;

    public String someMethod() {
        return this.getClass() + " says hello!";
    }
}

/**
 * Synchronized Accessor Singleton
 * + Ленивая инициализация
 * - Низкая производительность (критическая секция) в наиболее типичном доступе
 */
class SASingleton {
    private static SASingleton instance;

    public static synchronized SASingleton getInstance() {
        if (instance == null) {
            instance = new SASingleton();
        }
        return instance;
    }

    public String someMethod() {
        return this.getClass() + " says hello!";
    }
}

/**
 * Double Checked Locking Singleton
 * + Ленивая инициализация
 * + Высокая производительность
 * - Поддерживается только с JDK 1.5
 */
class DCSingleton {
    private static volatile DCSingleton instance;

    public static DCSingleton getInstance() {
        DCSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DCSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DCSingleton();
                }
            }
        }
        return localInstance;
    }

    public String someMethod() {
        return this.getClass() + " says hello!";
    }
}

/**
 * On Demand Holder idiom Singleton
 * + Ленивая инициализация
 * + Высокая производительность
 * - Невозможно использовать для не статических полей класса
 */
class OHSingleton {

    public static class SingletonHolder {
        public static final OHSingleton HOLDER_INSTANCE = new OHSingleton();
    }

    public static OHSingleton getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public String someMethod() {
        return this.getClass() + " says hello!";
    }
}

public class Main {

    public static void printTime(String taskName, Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(" --- " + taskName + " takes: " + duration + " ms");
    }

    public static void main(String[] args) {
        printTime("Static Field Singleton", () -> {
            SFSingleton sfSingleton = SFSingleton.INSTANCE;
            System.out.println(sfSingleton.someMethod());
        });

        printTime("Enum Singleton", () -> {
            ESingleton eSingleton = ESingleton.INSTANCE;
            System.out.println(eSingleton.someMethod());
        });

        printTime("Synchronized Accessor Singleton", () -> {
            SASingleton saSingleton = SASingleton.getInstance();
            System.out.println(saSingleton.someMethod());
        });

        printTime("Double Checked Locking Singleton", () -> {
            DCSingleton dcSingleton = DCSingleton.getInstance();
            System.out.println(dcSingleton.someMethod());
        });

        printTime("On Demand Holder idiom Singleton", () -> {
            OHSingleton ohSingleton = OHSingleton.getInstance();
            System.out.println(ohSingleton.someMethod());
        });
    }
}
