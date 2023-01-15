import { combineReducers } from 'redux'
import { pageReducer } from './pageReducer'

export const rootReducer = combineReducers({
    page: pageReducer,
});