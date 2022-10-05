SELECT student.name, student.age, faculty.name
FROM student
         INNER JOIN faculty ON student.faculty = faculty.id

SELECT student.name, student.age, avatar.data
FROM student
         INNER JOIN avatar ON avatar.student_id = student.id