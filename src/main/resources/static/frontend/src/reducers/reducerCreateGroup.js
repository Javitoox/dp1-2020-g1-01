export default function (state = null, action) {
    switch (action.type) {
            case "CREATED_GROUP_SELECTED": 
                    return action.payload;
    } return state;
    
}