import {
    SET_MODAL_TYPE,
    SET_TAB_NUMBER,
    SET_IS_MODAL_OPEN,
    SET_IS_ID_HIDDEN,
    SET_IS_DATE_HIDDEN,
    SET_IS_BETWEEN_MODAL,
    SET_INSTANCE,
    DATA_FAILURE,
    SET_DATA,
    NEED_REFRESH,
    SET_IS_TICKET_MODAL_OPEN
} from '../action/pageAction'

export const initialInstance = {
    id: "", name: "",
    x: "", y: "",
    creationDate: "",
    fromId: "",
    fromX: "", fromY: "",
    fromZ: "",
    toId: "", toX: "",
    toY: "", toZ: "",
    toName: "", distance: ""
}

const initialState = {
    modal_type: false,
    tab_number: 1,
    msg: '',
    instance: initialInstance,
    data: [],
    need_refresh: false,
    is_modal_open: false,
    is_ticket_modal_open: false,
    is_id_hidden: true,
    is_date_hidden: true,
    is_between_modal: true,
};

export function pageReducer(state = initialState, action) {
    switch (action.type) {
        case SET_MODAL_TYPE:
            return {...state, modal_type: action.payload}
        case SET_IS_MODAL_OPEN:
            return {...state, is_modal_open: action.payload}
        case SET_IS_TICKET_MODAL_OPEN:
            return {...state, is_ticket_modal_open: action.payload}
        case SET_IS_ID_HIDDEN:
            return {...state, is_id_hidden: action.payload}
        case SET_IS_DATE_HIDDEN:
            return {...state, is_date_hidden: action.payload}
        case SET_IS_BETWEEN_MODAL:
            return {...state, is_between_modal: action.payload}
        case SET_TAB_NUMBER:
            return {...state, tab_number: action.payload}
        case SET_INSTANCE:
            return {...state, instance: action.payload}
        case DATA_FAILURE:
            return {...state, msg: action.payload}
        case SET_DATA:
            return {...state, data: action.payload}
        case NEED_REFRESH:
            return {...state, need_refresh: action.payload}
        // case DATA_SUCCESS:
        //     return {...state, msg: action.payload}
        default:
            return state;
    }
}