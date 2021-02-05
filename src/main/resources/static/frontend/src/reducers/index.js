import { combineReducers } from 'redux';
import AssignStudentReducer from './reducerAssignStudent';
import StudentReducer from './reducerStudent'
import StudentsReducer from './reducerStudents'
import UserSelectedReducer from './reducerLogin'
import CreateGroupSelectedReducer from './reducerCreateGroup'
import DeleteGroupSelected from './reducerDeleteGroup'
const allReducers = combineReducers({ //combinamos todos nuestros reducers aqui, llamandolos con distintos nombres segun lo que sean, tal como lo llamemos se almacenaran en el store y deberemos usar ese nombre para pillarlos de alli en el componente que se (ejemplo en el EditStudent)

    student: StudentReducer,
    students: StudentsReducer,
    astudent: AssignStudentReducer,
    uselected: UserSelectedReducer,
    cgselected: CreateGroupSelectedReducer,
    dgselected: DeleteGroupSelected
})
export default allReducers;