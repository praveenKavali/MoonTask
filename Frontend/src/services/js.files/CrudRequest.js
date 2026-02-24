import useSWRMutation from 'swr/mutation';
import api from './Fetcher';

/**
 * Sends a POST request to register a new user and login request to register to the server.
 * @param {String} url - The endpoing URL.
 * @param {Object} context - SWR mutation context.
 * @param {Object} context.arg - Contains the user registration data(username, email and password).
 * @returns {Promise<String>} Contains the backend response.*/ 
async function postUser(url, { arg }){
    return await api.post(url, arg).then(res => res.data);
}

async function updateUser(url, { arg }) {
    return await api.put(url, arg).then(res => res.data);
}

async function deleteUser(url) {
    return await api.delete(url).then(res => res.data);
}

/**
 * Hook to handle user registration using SWR Mutation.
 * @returns {import('swr/mutation').SWRMutationResponse} object contains trigger, data, error, isMutating.
 */
export function useRegisterMutation() {
    return useSWRMutation('/register', postUser);
}

export function useUpdateMutation() {
    return useSWRMutation('/update', updateUser);
}

export function useDeleteMutation() {
    return useSWRMutation('/delete', deleteUser);
}