import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        List<Person> personList = getPerson();

        System.out.println("Все ФИО:");
        personList.stream().map(person -> person.getLastName() + " " + person.getFirstName() + " " + person.getSecondName())
                .forEach(System.out::println);

        System.out.println("---------------------------");

        System.out.println("Фио людей, родившихся в 1992 году:");
        Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.1991");
        Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1993");
        personList.stream().filter(person -> person.getBirthday()
                .before(date2) && person.getBirthday().after(date1))
                .map(person -> person.getLastName() + " " + person.getFirstName() + " " + person.getSecondName())
                .forEach(System.out::println);

        System.out.println("---------------------------");

        System.out.println("Коллекция адресов:");
        List<String> addressCollection = personList.stream().map(Person::getAddress).distinct().collect(Collectors.toList());

        addressCollection.forEach(System.out::println);
    }

    private static List<Person> getPerson() throws IOException, ParseException {

        BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\Входные данные.csv"));

        List<Person> personList = new ArrayList<>();
        String line;
        int index = 0;

        while ((line = reader.readLine()) != null) {
            Person person = new Person();
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    person.setFirstName(data);
                else if (index == 1)
                    person.setSecondName(data);
                else if (index == 2)
                    person.setLastName(data);
                else if (index == 3) {
                    Date onlyDate = new SimpleDateFormat("dd.MM.yyyy").parse(data);
                    person.setBirthday(onlyDate);
                } else if (index == 4)
                    person.setAddress(data);
                index++;
            }
            index = 0;
            personList.add(person);
        }
        reader.close();

        return personList;
    }

}

