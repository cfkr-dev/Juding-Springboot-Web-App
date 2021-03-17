# JUDING

![Judo Federation of Madrid](documentation_imgs/judoFederationLogo.png "Judo Federation of Madrid")

**JUDO FEDERATION OF MADRID**

This application try to replace the [official website](https://www.fmjudo.es/) of Judo Federation of Madrid, adding new
tools for competitions, competitors and referees management. In order to unify the diferents existing tools such as the
register of the differents kinds of users and the monitoring of the results of the competitions.

## The team: developers

| Full Name | Corporative email | Github nickname |
| - | - | - |
| Ismael González Sastre | i.gonzalezs.2018@alumnos.urjc.es | [Ismaelgzse](https://github.com/Ismaelgzse) |
| Diego Guerrero Carrasco | d.guerrero.2018@alumnos.urjc.es | [diego-guerrero](https://github.com/diego-guerrero) |
| Alberto Pérez Pérez | a.perezpe.2018@alumnos.urjc.es | [C0nf1cker](https://github.com/C0nf1cker) |
| José Luis Toledano Díaz | jl.toledano.2018@alumnos.urjc.es | [jolutoher18](https://github.com/jolutoher18) |

The group is organised using the Trello tool. Our board can be viewed here: [Trello](https://trello.com/b/rGpiD6eO/daw-grupo-2).

___

## Stage 0: project description

### Entities

* **Users**. Users are the *cornerstone* of our application. There are different types of users and all of them are part
  of this great community.
* **Competitions**. Competitions are the tournaments where competitors fight. There are a lot of competitions for many
  people: for kids, for young people and for adults.
* **Fights**. Competitions are made of fights. Each bout is played with two competitors and there is one referee per
  fight.
* **Posts**. All types of users will be able to see small news items about the world of judo and recent activities on the
  same topic on the main page of the application.

### User roles and permissions

* **Unregistered users**. Anonymous users can read generic information about judo and about the Federation.
* **Competitors**. These users are able to ask for playing in a competition and to see the raffle for the competitions.
  Furthermore, they can see their own profile with their personal information and competition history.
* **Referees**. These users can assign points when they are refereeing a fight. They can also see their personal
  information and a history of fights refereed in their own profiles.
* **Administrator**. This user has the *absolute* control of the application, with capacities like:
    * Grant access to referees (their sign up process must be confirmed).
    * Create, modify and delete competitions.
    * Modify and delete competitors.

### Images

* The application **administrator** will be able to set a big image to represent each competition.
* Referees and competitors will have an avatar image (they will be able to change and delete it whenever they want).
  Having a profile image is compulsory to take part in a competition (both for competitors and for referees).

### Graphics

There will be shown two different graphics:

* Line graph showing the evolution during past competitions of each competitor.
* Bar graph with the number of bronze, silver and gold medals obtained during past competitions.

### Complementary technology

* The application will generate assistance justifications in PDF format.
* The ranking of a competition can be exported as PDF.
* Users will receive an e-mail verification when signing up in the application.
* Referees will receive an e-mail verification when their applications are solved (even if are accepted or if are
  rejected).

### Advanced algorithm

* When the result of a fight is sent by a referee, the clasification score and tree will be automatically updated.
* The bar chart of medals (and the amount of medals obtained) will be dynamically obtained by comparing the results of
  each competition and resolving by the result if the competitor had scored 1st, 2nd or in any of the 3rd positions.

---

## Stage 1: Layout of pages with HTML and CSS ("frontend")

The different screens that make up this web application have been coded with a letter and two digits according to the
following criteria.

* A-XX: Screens that can be viewed by all types of users (registered and unregistered).
* CRAD-XX: Screens that can be seen by competitors, referees and administrator.
* C-XX: Screens that competitors can see.
* R-XX: Screens that referees can see.
* AD-XX: Screens that administrator can see.
* E-XX: Screens of errors.

### Screens

The application consists of the following screens:

* A-01: Homepage.
  ![](documentation_imgs/stage2/screenshots/A-01.png)
* A-02: News view.
  ![](documentation_imgs/stage2/screenshots/A-02.png)
* A-03: Terms and conditions of use.
  ![](documentation_imgs/stage2/screenshots/A-03.png)
* A-04: Cookies policy.
  ![](documentation_imgs/stage2/screenshots/A-04.png)
* A-05: Login.
  ![](documentation_imgs/stage2/screenshots/A-05.png)
* A-06: Competitors' registration.
  ![](documentation_imgs/stage2/screenshots/A-06.png)
* A-07: Referee registration.
  ![](documentation_imgs/stage2/screenshots/A-07.png)
* CRAD-01: License validation. First step to change a user's password.
  ![](documentation_imgs/stage2/screenshots/CR-01.png)
* CRAD-02: Security question. Second step to change a user's password.
  ![](documentation_imgs/stage2/screenshots/CR-02.png)
* CRAD-03: Password change. Third and last step to change a user's password.
  ![](documentation_imgs/stage2/screenshots/CR-03.png)
* CRAD-04: Competition information. This screen reflects the details of a competition as well as the draw for the
  competition, the results and the venue.
  ![](documentation_imgs/stage2/screenshots/CR-04.png)
* CRAD-05: Ranking of competitors.
  ![](documentation_imgs/stage2/screenshots/CR-05.png)
* C-01: Competitors' homepage. On this page you can see some details of their profile, their statistics and the
  competitions they can enter or have already entered.
  ![](documentation_imgs/stage2/screenshots/C-01.png)
* C-02: Competitor profile.
  ![](documentation_imgs/stage2/screenshots/C-02.png)
* C-03: Profile edition screen. Competitors can change some of the aspects of their own profile here.
  ![](documentation_imgs/stage2/screenshots/C-03.png)
* R-01: Referees' homepage. On this page, referees can see some details of their profile, their statistics and the
  competitions they can enter or have already entered.
  ![](documentation_imgs/stage2/screenshots/R-01.png)
* R-02: Referee profile.
  ![](documentation_imgs/stage2/screenshots/R-02.png)
* R-03: Profile edition screen. Referees can change some of the aspects of their own profile here.
  ![](documentation_imgs/stage2/screenshots/R-03.png)
* R-04: Fight control panel. The referee can manage from here by recording the scores and controlling the fight times.
  ![](documentation_imgs/stage2/screenshots/R-04.png)
* AD-01: List of competitors.
  ![](documentation_imgs/stage2/screenshots/AD-01.png)
* AD-02: User profile in competitor list.
  ![](documentation_imgs/stage2/screenshots/AD-02.png)
* AD-03: User editing in competitor list.
  ![](documentation_imgs/stage2/screenshots/AD-03.png)
* AD-04: Applications and list of referees.
  ![](documentation_imgs/stage2/screenshots/AD-04.png)
* AD-05: User profile in referee list.
  ![](documentation_imgs/stage2/screenshots/AD-05.png)
* AD-06: User editing in referee list.
  ![](documentation_imgs/stage2/screenshots/AD-06.png)
* AD-07: List of competitions.
  ![](documentation_imgs/stage2/screenshots/AD-07.png)
* AD-08: New competition form.
  ![](documentation_imgs/stage2/screenshots/AD-08.png)
* AD-09: List of news.
  ![](documentation_imgs/stage2/screenshots/AD-09.png)
* AD-10: New news form.
  ![](documentation_imgs/stage2/screenshots/AD-10.png)
* E-1: 403 error.
  ![](documentation_imgs/stage2/screenshots/E-01.png)
* E-2: 404 error.
  ![](documentation_imgs/stage2/screenshots/E-02.png)
* E-3: 500 error.
  ![](documentation_imgs/stage2/screenshots/E-03.png)

### Navigation diagram

The following diagram shows how different users can navigate through the different pages of the application according to
their role.
![](documentation_imgs/stage2/navigationDiagram.png)

___

## Stage 2: backend

### Top 5 Commits & Files

| Name | Top 5 Commits | Top 5 Files |
| - | - | - |
| Ismael | <ul><li>[Commit1Description](Commit1Link)</li><li>[Commit2Description](Commit2Link)</li><li>[Commit3Description](Commit3Link)</li><li>[Commit4Description](Commit4Link)</li><li>[Commit5Description](Commit5Link)</li></ul> | <ul><li>[File1Description](File1Link)</li><li>[File2Description](File2Link)</li><li>[File3Description](File3Link)</li><li>[File4Description](File4Link)</li><li>[File5Description](File5Link)</li></ul> |
| Diego | <ul><li>[Commit1Description](Commit1Link)</li><li>[Commit2Description](Commit2Link)</li><li>[Commit3Description](Commit3Link)</li><li>[Commit4Description](Commit4Link)</li><li>[Commit5Description](Commit5Link)</li></ul> | <ul><li>[File1Description](File1Link)</li><li>[File2Description](File2Link)</li><li>[File3Description](File3Link)</li><li>[File4Description](File4Link)</li><li>[File5Description](File5Link)</li></ul> |
| Alberto | <ul><li>[Commit1Description](Commit1Link)</li><li>[Commit2Description](Commit2Link)</li><li>[Commit3Description](Commit3Link)</li><li>[Commit4Description](Commit4Link)</li><li>[Commit5Description](Commit5Link)</li></ul> | <ul><li>[File1Description](File1Link)</li><li>[File2Description](File2Link)</li><li>[File3Description](File3Link)</li><li>[File4Description](File4Link)</li><li>[File5Description](File5Link)</li></ul> |
| José Luis | <ul><li>[Commit1Description](Commit1Link)</li><li>[Commit2Description](Commit2Link)</li><li>[Commit3Description](Commit3Link)</li><li>[Commit4Description](Commit4Link)</li><li>[Commit5Description](Commit5Link)</li></ul> | <ul><li>[File1Description](File1Link)</li><li>[File2Description](File2Link)</li><li>[File3Description](File3Link)</li><li>[File4Description](File4Link)</li><li>[File5Description](File5Link)</li></ul> |

### Navigation Diagram Updates
(Screenshots are included in Stage 1 section).
New navigation diagram:
![](documentation_imgs/stage2/navigationDiagram2.png)

### Entity-Relationship Diagram
Entity-Relationship Diagram

### Classes and templates diagram
Classes and templates diagram

### Guide: getting started with Juding
Guide: getting started with Juding.
