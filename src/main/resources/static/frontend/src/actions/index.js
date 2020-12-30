export const selectStudent = (student) => { //se debe poner como constante y el export para que funcione sin problemas
    console.log("has seleccionado a " , student.nickUsuario);
    return{
        type: "STUDENT_SELECTED",
        payload: student //el payload contiene la informacion del objeto que hemos pasado
    }
};
export const selectAssignedStudent = (student) => { //se debe poner como constante y el export para que funcione sin problemas
    console.log("has seleccionado al " , student.nickUsuario);
    return{
        type: "ASSIGN_STUDENT_SELECTED",
        payload: student //el payload contiene la informacion del objeto que hemos pasado
    }
};

export const selectUserLogin = (nickUsuario) => { //se debe poner como constante y el export para que funcione sin problemas
    console.log("has seleccionado al " , nickUsuario);
    return{
        type: "ASSIGN_STUDENT_SELECTED",
        payload: nickUsuario //el payload contiene la informacion del objeto que hemos pasado
    }
};