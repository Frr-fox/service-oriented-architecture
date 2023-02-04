import axios from "axios";

export const SET_MODAL_TYPE = 'SET_MODAL_TYPE'
export const SET_IS_MODAL_OPEN = 'SET_IS_MODAL_OPEN'
export const SET_IS_TICKET_MODAL_OPEN = 'SET_IS_TICKET_MODAL_OPEN'
export const SET_IS_ID_HIDDEN = 'SET_IS_ID_HIDDEN'
export const SET_IS_DATE_HIDDEN = 'SET_IS_DATE_HIDDEN'
export const SET_IS_BETWEEN_MODAL = 'SET_IS_BETWEEN_MODAL'
export const SET_TAB_NUMBER = 'SET_TAB_NUMBER'
export const SET_INSTANCE = 'SET_INSTANCE'
export const SET_DATA = 'SET_DATA'
export const DATA_FAILURE = 'DATA_FAILURE'
export const NEED_REFRESH = 'NEED_REFRESH'
// export const DATA_SUCCESS = 'DATA_SUCCESS'

export function setModalType(modal_type) {
    return {
        type: SET_MODAL_TYPE,
        payload: modal_type
    }
}
export function setIsModalOpen(isOpen) {
    return {
        type: SET_IS_MODAL_OPEN,
        payload: isOpen
    }
}

export function setIsTicketModalOpen(isOpen) {
    return {
        type: SET_IS_TICKET_MODAL_OPEN,
        payload: isOpen
    }
}

export function setIsIdHidden(hidden) {
    return {
        type: SET_IS_ID_HIDDEN,
        payload: hidden
    }
}

export function setIsDateHidden(hidden) {
    return {
        type: SET_IS_DATE_HIDDEN,
        payload: hidden
    }
}

export function setIsBetweenModal(isBetweenModal) {
    return {
        type: SET_IS_BETWEEN_MODAL,
        payload: isBetweenModal
    }
}

export function setTabNumber(tab_number) {
    return {
        type: SET_TAB_NUMBER,
        payload: tab_number
    }
}

export function setInstance(instance) {
    return {
        type: SET_INSTANCE,
        payload: instance
    }
}

export function setMessage(message) {
    return {
        type: DATA_FAILURE,
        payload: message
    }
}

export function setData(data) {
    return {
        type: SET_DATA,
        payload: data
    }
}

export function needRefresh(refresh) {
    return {
        type: NEED_REFRESH,
        payload: refresh
    }
}

// export function processData(data, path, method) {
//     return dispatch => {
//         axios({
//             url: 'http://localhost:31470/api/v1' + path,
//             data: data,
//             method: method,
//         })
//             .then(res => {
//                 if (res.status === 200) {
//                     dispatch({
//                         type: DATA_SUCCESS,
//                     })
//                 } else if (res.status === 403) {
//                     dispatch({
//                         type: DATA_FAILURE,
//                         payload: res.error
//                     })
//                 }
//             })
//     }
// }