export const selectStudent = (student) => { //se debe poner como constante y el export para que funcione sin problemas
    return{
        type: "STUDENT_SELECTED",
        payload: student //el payload contiene la informacion del objeto que hemos pasado
    }
};
export const selectAssignedStudent = (student) => { //se debe poner como constante y el export para que funcione sin problemas
    return{
        type: "ASSIGN_STUDENT_SELECTED",
        payload: student //el payload contiene la informacion del objeto que hemos pasado
    }
};
export const studentInfo = (student) => { //se debe poner como constante y el export para que funcione sin problemas
    return{
        type: "STUDENT_INFO",
        payload: student //el payload contiene la informacion del objeto que hemos pasado
    }
};
export const selectUserLogin = (nickUsuario) => { //se debe poner como constante y el export para que funcione sin problemas
    return{
        type: "ASSIGN_STUDENT_SELECTED",
        payload: nickUsuario //el payload contiene la informacion del objeto que hemos pasado
    }
};
export const selectCreatedGroup = (nombreGrupo) => { //se debe poner como constante y el export para que funcione sin problemas
    console.log("has seleccionado al " , nombreGrupo);
    return{
        type: "CREATED_GROUP_SELECTED",
        payload: nombreGrupo //el payload contiene la informacion del objeto que hemos pasado
    }
};

export const selectDeletedGroup = (nombreGrupo) => { //se debe poner como constante y el export para que funcione sin problemas
    console.log("has seleccionado al " , nombreGrupo);
    return{
        type: "DELETED_GROUP_SELECTED",
        payload: nombreGrupo //el payload contiene la informacion del objeto que hemos pasado
    }
};