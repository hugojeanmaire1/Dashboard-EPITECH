/**
 * @interface services interface
 */
export interface Services {
  name: string;
  widgets?: Widget[];
}

/**
 * @interface Widgets Services
 */
export interface Widget {
  title: string;
  id?: string;
  name: string;
  description: string,
  params?: any,
  position?: any;
}
