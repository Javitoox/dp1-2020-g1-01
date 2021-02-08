export default function StudentReducer(state = {}, action) {
        switch (action.type) {
                case "STUDENT_SELECTED":
                        return action.payload;
                case "STUDENT_INFO":
                        return action.payload;
                default:
                        return state;
        }

}