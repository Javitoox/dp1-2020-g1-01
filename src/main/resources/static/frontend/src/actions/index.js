export const selectStudent = (student) => {
console.log("has seleccionado a " , student.nickUsuario);
    return{
        type: "STUDENT_SELECTED",
        payload: student
    }
};
export default selectStudent