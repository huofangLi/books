import { Moment } from 'moment';
import { ITopic } from 'app/shared/model/topic.model';

export interface ITopic {
  id?: number;
  userId?: number;
  title?: string;
  content?: string;
  createTime?: Moment;
  parentId?: ITopic;
}

export class Topic implements ITopic {
  constructor(
    public id?: number,
    public userId?: number,
    public title?: string,
    public content?: string,
    public createTime?: Moment,
    public parentId?: ITopic
  ) {}
}
