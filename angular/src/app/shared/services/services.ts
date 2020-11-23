export interface Services {
  name: string;
  widgets?: Widget[];
}

export interface Widget {
  title: string;
  id?: string;
  name: string;
  description: string,
  params: any,
  positions: any;
}
