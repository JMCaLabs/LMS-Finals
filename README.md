# LMS-Finals
LMS GUI F2

MEMBERS:
CALABROSO, JAUSH MAR D.
GEROGALIN, JOVITO V.
GOTINGCO, RYAN REY A.
MALINAO, JEKAIAH LI HOWARD R.

11/13/2026 PRESENT:
CALABROSO, JAUSH MAR D.
GEROGALIN, JOVITO V.
GOTINGCO, RYAN REY A.
MALINAO, JEKAIAH LI HOWARD R.

11/20/2026 PRESENT:
CALABROSO, JAUSH MAR D.
GEROGALIN, JOVITO V.
GOTINGCO, RYAN REY A.
MALINAO, JEKAIAH LI HOWARD R.

11/20/2026 PROGRESS DETAILS:
Implemented createnewUser and LoginException, made Progress to the Login UI.

11/27/2026 PROGRESS DETAILS:
Implemented the creational design pattern:
Factory: When a user is created, depending on the radio button being selected at the current time, it creates a new Student or new Teacher and adds it to a list. This can be found in the createNewUser class and form.
Implemented the structural design patterns:
Facade: The implementation of getUsers(), getCourse(), and saveListData() allows other classes to access these without handling the saving and loading of the files itself.
Flyweight: The implementation of Assignment class, specifically it relating to Submission. It does not contain a list of submission and the submission is saved inside the student class, with the submission class containing a reference to the assignment it is being submitted to, this removes the need to create a copy of the assignment as a submission for every single student, rather we just need to create a submission that refers to that specific assignment.

CAPSTONE DETAILS:
This capstone is a Learning Management System created using GUI Java Swing.
