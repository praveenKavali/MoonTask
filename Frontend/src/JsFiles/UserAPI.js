import backendCall from "./BaseAxios";
import useSWRMutation from 'swr/mutation';

/**
 * This method is used to send post request to the backend.
 * @param {string} url - the endpoint url. 
 * @param {*} arg - SWR mutation context(contains the form data).
 * @returns {@Promise<string>} contains the backend response.
 */
async function postRequest(url, {arg}){
    const response = await backendCall.post(url, arg);
    return response.data;
}

/**
 * Custom made hook to handle user post request. I will interact with user related post api in backend.
 * @param {boolean} isRegister - used for determaining end point of the post request.
 * @returns backend response(data or error).
 */
export function useAuthHandler(isRegister) {
    const endpoint = isRegister ? '/register' : 'login';
    return useSWRMutation(endpoint, postRequest);
}