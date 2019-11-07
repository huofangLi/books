import { Moment } from 'moment';

export interface ICourse {
  id?: number;
  courseClassification?: string;
  courseName?: string;
  courseImage?: string;
  courseDescribe?: string;
  coursePrice?: number;
  courseChapter?: string;
  courseIntroduction?: string;
  courseVideo?: string;
  presenter?: string;
  presenterImage?: string;
  presenterIntroduction?: string;
  createTime?: Moment;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public courseClassification?: string,
    public courseName?: string,
    public courseImage?: string,
    public courseDescribe?: string,
    public coursePrice?: number,
    public courseChapter?: string,
    public courseIntroduction?: string,
    public courseVideo?: string,
    public presenter?: string,
    public presenterImage?: string,
    public presenterIntroduction?: string,
    public createTime?: Moment
  ) {}
}
