import { combineReducers } from 'redux';
import StudentReducer from './reducerStudent'
import StudentsReducer from './reducerStudents'
const allReducers = combineReducers({

    student: StudentReducer,
    students: StudentsReducer
})
export default allReducers;