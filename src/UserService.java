import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UserService implements Serializable {
    private static String file_name = "e:\\1.txt";

    private static Set<User> usersList = new HashSet<>();

    private void saveListUser() {
        if (usersList.size() != 0) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new FileOutputStream(new File(file_name)));

                objectOutputStream.writeObject(usersList);
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Список пользователей пуст");
    }

    private void loadListUser() {

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(file_name));
            usersList = (Set<User>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException e) {
            System.out.println("Не удатся найти файл");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private int readInteger(Scanner scanner, String message) {
        int a = 0;
        do {
            System.out.println(message);
            String a1 =  scanner.nextLine();

            try {
                a = Integer.parseInt(a1);
                if (a < 0) System.out.println("Попробуйте еще раз");
            } catch (NumberFormatException e) {
                System.out.println("Попробуйте еще раз");
                break;
            }
            return a;
        } while (true);
        return a;
    }

    private int countInput(Scanner scanner) {
        int s;
        boolean d = true;

        do {
            s = readInteger(scanner, "от 1 до 3");
            if (s <= 3 && s > 0) {
                d = false;
            }
        } while (d);

        return s;
    }

    private void createUser(Scanner scanner) {

        User user = new User();
        System.out.println("Введите Имя");
        user.setFirstName(validNoEmpty(scanner,scanner.nextLine()));
        System.out.println("Введите Фамилию");
        user.setLastName(validNoEmpty(scanner,scanner.nextLine()));
        System.out.println("Введите Электронную почту");
        System.out.println("Электронная почта вида *******@*****.***");
        user.setEmail(validEmail(scanner, validNoEmpty(scanner,scanner.nextLine())));
        System.out.println("Введите количество Ролей");
        int s = countInput(scanner);

        for (int i = 0; i < s; i++) {
            System.out.println("Введите Роль " + (i + 1) + " (не должны повторяться)");
            user.setRole(validNoEmpty(scanner,scanner.nextLine()));
        }
        System.out.println("Введите количество Телефонов");

        int d = countInput(scanner);
        for (int i = 0; i < d; i++) {
            System.out.println("Введите Телефон " + (i + 1) + " (не должны повторяться)");
            System.out.println("Телефон вида 375** *******");
            user.setTel(validTel(scanner, validNoEmpty(scanner,scanner.nextLine())));
        }
        usersList.add(user);

    }

    private void infoUser(Scanner scanner) {

        if (usersList.size() != 0) {
            System.out.println("Введите Имя");
            String temp = validNoEmpty(scanner,scanner.nextLine());
            int count = 0;
            for (User user : usersList) {
                if (user.getFirstName().equals(temp)) {
                    System.out.println(user);
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("Нет такого пользователя");
            }
        } else System.out.println("Список пользователей пуст");
    }

    private void updateUser(Scanner scanner) {

        if (usersList.size() != 0) {
            System.out.println("Имя пользователя для обновления");
            String temp = validNoEmpty(scanner,scanner.nextLine());
            int count = 0;
            Iterator iterator = usersList.iterator();
            while (iterator.hasNext()) {
                User element = (User) iterator.next();
                if (element.getFirstName().equals(temp)) {
                    System.out.println(element);
                    System.out.println("Введите Имя");
                    element.setFirstName(validNoEmpty(scanner,scanner.nextLine()));
                    System.out.println("Введите Фамилию");
                    element.setLastName(validNoEmpty(scanner,scanner.nextLine()));
                    System.out.println("Введите Электронную почту");
                    System.out.println("Электронная почта вида *******@*****.***");
                    element.setEmail(validEmail(scanner, validNoEmpty(scanner,scanner.nextLine())));
                    System.out.println("Введите количество Ролей");
                    element.rolTelClean();
                    int s = countInput(scanner);
                    for (int i = 0; i < s; i++) {
                        System.out.println("Введите Роль " + (i + 1) + " (не должны повторяться)");
                        element.setRole(validNoEmpty(scanner,scanner.nextLine()));
                    }
                    System.out.println("Введите количество Телефонов");
                    int d = countInput(scanner);
                    for (int i = 0; i < d; i++) {
                        System.out.println("Введите Телефон " + (i + 1) + " (не должны повторяться)");
                        System.out.println("Телефон вида 375** *******");
                        element.setTel(validTel(scanner, validNoEmpty(scanner,scanner.nextLine())));
                    }
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("Нет такого пользователя");
            }
        } else System.out.println("Список пользователей пуст");
    }

    private void deleteUser(Scanner scanner) {

        if (usersList.size() != 0) {
            System.out.println("Введите Имя");
            String temp = validNoEmpty(scanner,scanner.nextLine());
            int count = 0;
            Iterator iterator = usersList.iterator();
            while (iterator.hasNext()) {
                User element = (User) iterator.next();
                if (element.getFirstName().equals(temp)) {
                    iterator.remove();
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("Нет такого пользователя");
            }
        } else System.out.println("Список пользователей пуст");
    }

    private void infoAllUser() {
        if (usersList.size() != 0) {
            for (User user : usersList) {
                System.out.println(user);
            }
        } else System.out.println("Список пользователей пуст");
    }

    private String validEmail(Scanner scanner, String s) {
        boolean d = true;
        Pattern pattern = Pattern.compile("(.*)+(@)+(.*)+(\\.)(.*){2,3}\\S");
        Matcher matcher;
        while (d) {
            matcher = pattern.matcher(s);
            if (!matcher.find()) {
                System.out.println("Введен не валидный электронный адрес");
                s = validNoEmpty(scanner,scanner.nextLine());
            } else d = false;
        }
        return s;
    }

    private String validTel(Scanner scanner, String s) {
        boolean d = true;
        Pattern pattern = Pattern.compile("^375\\d{2} \\d{7}$");
        Matcher matcher;
        while (d) {
            matcher = pattern.matcher(s);
            if (!matcher.find()) {
                System.out.println("Введен не валидный телефон");
                s = validNoEmpty(scanner,scanner.nextLine());
            } else d = false;
        }
        return s;
    }

    private String validNoEmpty(Scanner scanner, String s) {
                while (s.equals("")||s.equals(" ")) {
                System.out.println("Поле не может быть пустым");
                s = validNoEmpty(scanner,scanner.nextLine());
            }
        return s;
    }

    void start() {
        Scanner scanner = new Scanner(System.in);

        boolean s = true;
        int w;
        while (s) {
            w = readInteger(scanner, "\nЧтобы вы хотели сделать?" +
                    "\n\r1 Создать нового пользователя" +
                    "\n\r2 Обновить данные пользоватея" +
                    "\n\r3 Удалить пользователя\n\r4 Получить данные пользователя по имени" +
                    "\n\r5 Получить всех ползователей в списке\n\r6 Соханить в файл" +
                    "\n\r7 Загрузить из файла\n\r8 Выход");
            switch (w) {
                case 1: {
                    System.out.println("Создание");
                    createUser(scanner);
                    break;
                }
                case 2: {
                    System.out.println("Обновление");
                    updateUser(scanner);
                    break;
                }
                case 3: {
                    System.out.println("Удаление");
                    deleteUser(scanner);
                    break;
                }
                case 4: {
                    System.out.println("Информация о пользователе по имени");
                    infoUser(scanner);
                    break;
                }
                case 5: {
                    System.out.println("Информация о всех пользователях");
                    infoAllUser();
                    break;
                }
                case 6: {
                    System.out.println("Сохранить");
                    saveListUser();
                    break;
                }
                case 7: {
                    System.out.println("Загрузить");
                    loadListUser();
                    break;
                }
                case 8: {
                    System.out.println("До свидания");
                    s = false;
                    break;
                }
            }
        }
    }
}
