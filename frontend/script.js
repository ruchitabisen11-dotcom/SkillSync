// ============================================
// SKILLSYNC FRONTEND
// ============================================


// Get students from browser storage

let students =
    JSON.parse(
        localStorage.getItem("skillsync_students")
    ) || [];


// ============================================
// SAVE DATA
// ============================================

function saveStudents() {

    localStorage.setItem(
        "skillsync_students",
        JSON.stringify(students)
    );

}


// ============================================
// GENERATE ID
// ============================================

function generateId() {

    return Date.now();

}


// ============================================
// ADD STUDENT
// ============================================

document
    .getElementById("studentForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const name =
            document
                .getElementById("studentName")
                .value
                .trim();


        const college =
            document
                .getElementById("studentCollege")
                .value
                .trim();


        const course =
            document
                .getElementById("studentCourse")
                .value
                .trim();


        const year =
            document
                .getElementById("studentYear")
                .value;


        const bio =
            document
                .getElementById("studentBio")
                .value
                .trim();


        const skills =
            document
                .getElementById("studentSkills")
                .value
                .split(",")
                .map(skill => skill.trim())
                .filter(skill => skill !== "");


        const student = {

            id: generateId(),

            name: name,

            college: college,

            course: course,

            year: year,

            bio: bio,

            skills: skills

        };


        students.push(student);


        saveStudents();


        alert("Student added successfully!");


        document
            .getElementById("studentForm")
            .reset();


        displayStudents();

        updateStatistics();


        showSection("students");

    });


// ============================================
// DISPLAY STUDENTS
// ============================================

function displayStudents() {

    const studentList =
        document.getElementById("studentList");


    const searchValue =
        document
            .getElementById("searchInput")
            .value
            .toLowerCase()
            .trim();


    let filteredStudents =
        students.filter(student => {

            const name =
                student.name.toLowerCase();


            const college =
                student.college.toLowerCase();


            const skills =
                student.skills
                    .join(" ")
                    .toLowerCase();


            return (
                name.includes(searchValue) ||
                college.includes(searchValue) ||
                skills.includes(searchValue)
            );

        });


    if (filteredStudents.length === 0) {

        studentList.innerHTML = `
            <div class="empty">
                <h3>No students found</h3>
                <p>Add a student to see the profile here.</p>
            </div>
        `;

        return;

    }


    studentList.innerHTML =
        filteredStudents
            .map(student => createStudentCard(student))
            .join("");

}


// ============================================
// CREATE STUDENT CARD
// ============================================

function createStudentCard(student) {

    const skillsHTML =
        student.skills
            .map(skill =>
                `<span class="skill">${escapeHTML(skill)}</span>`
            )
            .join("");


    return `

        <div class="student-card">

            <h3>
                ${escapeHTML(student.name)}
            </h3>


            <p>
                <strong>College:</strong>
                ${escapeHTML(student.college)}
            </p>


            <p>
                <strong>Course:</strong>
                ${escapeHTML(student.course)}
            </p>


            <p>
                <strong>Year:</strong>
                ${escapeHTML(student.year)}
            </p>


            <p>
                <strong>Bio:</strong>
                ${escapeHTML(student.bio)}
            </p>


            <div class="skills">

                ${skillsHTML}

            </div>


            <div class="card-actions">

                <button
                    class="edit-btn"
                    onclick="openEditModal(${student.id})"
                >
                    Edit
                </button>


                <button
                    class="delete-btn"
                    onclick="deleteStudent(${student.id})"
                >
                    Delete
                </button>

            </div>

        </div>

    `;

}


// ============================================
// DELETE STUDENT
// ============================================

function deleteStudent(id) {

    const student =
        students.find(
            student => student.id === id
        );


    if (!student) {

        alert("Student not found.");

        return;

    }


    const confirmDelete =
        confirm(
            `Delete ${student.name}?`
        );


    if (!confirmDelete) {

        return;

    }


    students =
        students.filter(
            student => student.id !== id
        );


    saveStudents();


    displayStudents();

    updateStatistics();


    alert("Student deleted successfully!");

}


// ============================================
// OPEN EDIT MODAL
// ============================================

function openEditModal(id) {

    const student =
        students.find(
            student => student.id === id
        );


    if (!student) {

        alert("Student not found.");

        return;

    }


    document
        .getElementById("editId")
        .value = student.id;


    document
        .getElementById("editName")
        .value = student.name;


    document
        .getElementById("editCollege")
        .value = student.college;


    document
        .getElementById("editCourse")
        .value = student.course;


    document
        .getElementById("editYear")
        .value = student.year;


    document
        .getElementById("editBio")
        .value = student.bio;


    document
        .getElementById("editSkills")
        .value = student.skills.join(", ");


    document
        .getElementById("editModal")
        .style.display = "block";

}


// ============================================
// CLOSE EDIT MODAL
// ============================================

function closeEditModal() {

    document
        .getElementById("editModal")
        .style.display = "none";

}


// ============================================
// UPDATE STUDENT
// ============================================

document
    .getElementById("editForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const id =
            Number(
                document
                    .getElementById("editId")
                    .value
            );


        const student =
            students.find(
                student => student.id === id
            );


        if (!student) {

            alert("Student not found.");

            return;

        }


        student.name =
            document
                .getElementById("editName")
                .value
                .trim();


        student.college =
            document
                .getElementById("editCollege")
                .value
                .trim();


        student.course =
            document
                .getElementById("editCourse")
                .value
                .trim();


        student.year =
            document
                .getElementById("editYear")
                .value;


        student.bio =
            document
                .getElementById("editBio")
                .value
                .trim();


        student.skills =
            document
                .getElementById("editSkills")
                .value
                .split(",")
                .map(skill => skill.trim())
                .filter(skill => skill !== "");


        saveStudents();


        closeEditModal();


        displayStudents();

        updateStatistics();


        alert("Student updated successfully!");

    });


// ============================================
// FIND SKILL MATCH
// ============================================

function findSkillMatch() {

    const input =
        document
            .getElementById("matchSkills")
            .value
            .toLowerCase()
            .trim();


    const result =
        document
            .getElementById("matchResults");


    if (input === "") {

        result.innerHTML = `
            <div class="empty">
                Please enter at least one skill.
            </div>
        `;

        return;

    }


    const requestedSkills =
        input
            .split(",")
            .map(skill => skill.trim())
            .filter(skill => skill !== "");


    const matches =
        students
            .map(student => {

                let matchedSkills = [];


                student.skills.forEach(skill => {

                    const studentSkill =
                        skill.toLowerCase();


                    requestedSkills.forEach(
                        requestedSkill => {

                            if (
                                studentSkill === requestedSkill ||
                                studentSkill.includes(requestedSkill) ||
                                requestedSkill.includes(studentSkill)
                            ) {

                                if (
                                    !matchedSkills.includes(skill)
                                ) {

                                    matchedSkills.push(skill);

                                }

                            }

                        }
                    );

                });


                const percentage =
                    requestedSkills.length === 0
                        ? 0
                        : Math.round(
                            (
                                matchedSkills.length /
                                requestedSkills.length
                            ) * 100
                        );


                return {

                    student: student,

                    matchedSkills: matchedSkills,

                    percentage: percentage

                };

            })
            .filter(match =>
                match.matchedSkills.length > 0
            )
            .sort(
                (a, b) =>
                    b.percentage - a.percentage
            );


    if (matches.length === 0) {

        result.innerHTML = `
            <div class="empty">

                <h3>
                    No skill match found
                </h3>

                <p>
                    Try different skills.
                </p>

            </div>
        `;

        return;

    }


    result.innerHTML =
        matches
            .map(match => {

                const student =
                    match.student;


                const skillsHTML =
                    student.skills
                        .map(skill =>
                            `<span class="skill">
                                ${escapeHTML(skill)}
                            </span>`
                        )
                        .join("");


                return `

                    <div class="student-card">

                        <h3>
                            ${escapeHTML(student.name)}
                        </h3>


                        <p>
                            <strong>College:</strong>
                            ${escapeHTML(student.college)}
                        </p>


                        <p>
                            <strong>Course:</strong>
                            ${escapeHTML(student.course)}
                        </p>


                        <p>
                            <strong>Match:</strong>
                            ${match.percentage}%
                        </p>


                        <p>
                            <strong>Matched Skills:</strong>
                            ${escapeHTML(
                                match.matchedSkills.join(", ")
                            )}
                        </p>


                        <div class="skills">

                            ${skillsHTML}

                        </div>

                    </div>

                `;

            })
            .join("");

}


// ============================================
// STATISTICS
// ============================================

function updateStatistics() {

    document
        .getElementById("totalStudents")
        .textContent = students.length;


    const allSkills = [];


    students.forEach(student => {

        student.skills.forEach(skill => {

            if (
                !allSkills
                    .map(s => s.toLowerCase())
                    .includes(skill.toLowerCase())
            ) {

                allSkills.push(skill);

            }

        });

    });


    document
        .getElementById("totalSkills")
        .textContent = allSkills.length;


    const colleges = [];


    students.forEach(student => {

        if (
            !colleges
                .map(c => c.toLowerCase())
                .includes(
                    student.college.toLowerCase()
                )
        ) {

            colleges.push(student.college);

        }

    });


    document
        .getElementById("totalColleges")
        .textContent = colleges.length;

}


// ============================================
// NAVIGATION
// ============================================

function showSection(sectionId) {

    const section =
        document.getElementById(sectionId);


    if (section) {

        section.scrollIntoView({
            behavior: "smooth"
        });

    }

}


// ============================================
// ESCAPE HTML
// ============================================

function escapeHTML(value) {

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");

}


// ============================================
// CLOSE MODAL WHEN CLICKING OUTSIDE
// ============================================

window.addEventListener(
    "click",
    function(event) {

        const modal =
            document.getElementById("editModal");


        if (event.target === modal) {

            closeEditModal();

        }

    }
);


// ============================================
// INITIAL LOAD
// ============================================

displayStudents();

updateStatistics();