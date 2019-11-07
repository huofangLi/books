import { Moment } from 'moment';

export interface ISpeciclTopic {
  id?: number;
  title?: string;
  content?: string;
  image?: string;
  createTime?: Moment;
}

export class SpeciclTopic implements ISpeciclTopic {
  constructor(public id?: number, public title?: string, public content?: string, public image?: string, public createTime?: Moment) {}
}
