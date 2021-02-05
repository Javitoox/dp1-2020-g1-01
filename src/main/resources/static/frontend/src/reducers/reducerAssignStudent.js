export default function (state = null, action) {
    switch (action.type) {
            case "ASSIGN_STUDENT_SELECTED": 
                    return action.payload; //de aqui pillamos el objeto y se lo pasamos al index
                    break;
    } return state;
    
}