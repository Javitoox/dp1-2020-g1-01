export default function CreateGroupSelectedReducer(state = null, action) {
    switch (action.type) {
        case "CREATED_GROUP_SELECTED":
            return action.payload;
        default:
            return state;
    }

}