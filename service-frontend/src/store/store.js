import {applyMiddleware, createStore} from 'redux'
import { rootReducer } from './reducer/rootReducer'
import thunk from 'redux-thunk'
import {syncModalType} from "../component/forms/FormRoute";

export const store = createStore(rootReducer, applyMiddleware(thunk));