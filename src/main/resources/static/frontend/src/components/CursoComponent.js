import axios from 'axios';
import {Component} from 'react';

export default class CourseComponent extends Component{
    getCourses(){
        return axios.get("http://localhost:8081/course/all").then(res => res.data);
    }
}