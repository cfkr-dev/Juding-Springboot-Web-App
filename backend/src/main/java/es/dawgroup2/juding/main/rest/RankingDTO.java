package es.dawgroup2.juding.main.rest;

public class RankingDTO {

    private final String nickname;
    private final Integer sum;
    private final Integer num;
    private final String licenseId;
    private final String name;
    private final String surname;
    private final String dni;
    private final String belt;
    private final String imageFile;

    public RankingDTO(Object[] parameters) {
        this(
                parameters[0].toString(),
                Integer.parseInt(parameters[1].toString()),
                Integer.parseInt(parameters[2].toString()),
                parameters[3].toString(),
                parameters[4].toString(),
                parameters[5].toString(),
                parameters[6].toString(),
                parameters[7].toString(),
                "/api/users/" + parameters[3] + "/image"
        );
    }

    public RankingDTO(String nickname, int sum, int num, String licenseId, String name, String surname, String dni, String belt, String imageFile) {
        this.nickname = nickname;
        this.sum = sum;
        this.num = num;
        this.licenseId = licenseId;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.belt = belt;
        this.imageFile = imageFile;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getNum() {
        return num;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDni() {
        return dni;
    }

    public String getBelt() {
        return belt;
    }

    public String getImageFile() {
        return imageFile;
    }
}
