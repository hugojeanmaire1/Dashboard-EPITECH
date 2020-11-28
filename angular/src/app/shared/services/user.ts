/**
 * @interface User interface
 */
export interface User {
  uid: string;
  email: string;
  displayName: string;
  photoUrl: string;
  emailVerified: boolean;
  services: string;
  widgets: string;
}
