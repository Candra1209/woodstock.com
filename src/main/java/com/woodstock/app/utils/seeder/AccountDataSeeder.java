package com.woodstock.app.utils.seeder;

import com.woodstock.app.entity.JobsEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AccountDataSeeder {

    public class AccountSeeder {
        public String username;
        public String password;
        public String fullname;
        public String email;
        public String contact;
        public List<JobsEnum> jobs;

        public AccountSeeder(String username, String password, String fullname, String email, String contact, List<JobsEnum> jobs) {
            this.username = username;
            this.password = password;
            this.fullname = fullname;
            this.email = email;
            this.contact = contact;
            this.jobs = jobs;
        }
    }

    public List<AccountSeeder> getSeed(){

        return new ArrayList<>(
                List.of(
                        new AccountSeeder(
                                "candra",
                                "candra",
                                "Candra Irawan",
                                "candraseka1209@gmail.com",
                                "6285705092824",
                                new ArrayList<>(List.of(JobsEnum.SCALLER))
                        ),
                        new AccountSeeder(
                                "andika",
                                "andika",
                                "Andika Saputra",
                                "andika.saputra@gmail.com",
                                "628123450001",
                                new ArrayList<>(List.of(JobsEnum.FALLER))
                        ),
                        new AccountSeeder(
                                "bima",
                                "bima",
                                "Bima Pratama",
                                "bima.pratama@gmail.com",
                                "628123450002",
                                new ArrayList<>(List.of(JobsEnum.BUCKER))
                        ),
                        new AccountSeeder(
                                "rizky",
                                "rizky",
                                "Rizky Maulana",
                                "rizky.maulana@gmail.com",
                                "628123450003",
                                new ArrayList<>(List.of(JobsEnum.OPERATOR))
                        ),
                        new AccountSeeder(
                                "ferdi",
                                "ferdi",
                                "Ferdiansyah",
                                "ferdiansyah@gmail.com",
                                "628123450004",
                                new ArrayList<>(List.of(JobsEnum.SCALLER, JobsEnum.OPERATOR))
                        ),
                        new AccountSeeder(
                                "yoga",
                                "yoga",
                                "Yoga Prasetyo",
                                "yoga.prasetyo@gmail.com",
                                "628123450005",
                                new ArrayList<>(List.of(JobsEnum.BUCKER, JobsEnum.FALLER))
                        ),
                        new AccountSeeder(
                                "rahmat",
                                "rahmat",
                                "Rahmat Hidayat",
                                "rahmat.hidayat@gmail.com",
                                "628123450006",
                                new ArrayList<>(List.of(JobsEnum.MANAGER_LOGPOND))
                        ),
                        new AccountSeeder(
                                "doni",
                                "doni",
                                "Doni Setiawan",
                                "doni.setiawan@gmail.com",
                                "628123450007",
                                new ArrayList<>(List.of(JobsEnum.MANAGER_CAMP))
                        ),
                        new AccountSeeder(
                                "fajar",
                                "fajar",
                                "Fajar Nugroho",
                                "fajar.nugroho@gmail.com",
                                "628123450008",
                                new ArrayList<>(List.of(JobsEnum.SCALLER, JobsEnum.GANISPH))
                        ),
                        new AccountSeeder(
                                "aldi",
                                "aldi",
                                "Aldi Kurniawan",
                                "aldi.kurniawan@gmail.com",
                                "628123450009",
                                new ArrayList<>(List.of(JobsEnum.OPERATOR, JobsEnum.GANISPH, JobsEnum.MANAGER_LOGPOND))
                        )
                )
        );

    }

}
