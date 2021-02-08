export default function AssignStudentReducer(state = null, action) {
    switch (action.type) {
        case "ASSIGN_STUDENT_SELECTED":
            return action.payload;
        default: return state;
    }

}