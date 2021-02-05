export default function (state = {}, action) {
        switch (action.type) {
                case "STUDENT_SELECTED": 
                        return action.payload; 
                        break;
                 case "STUDENT_INFO": 
                        return action.payload; 
                        break;
        } return state;
        
}