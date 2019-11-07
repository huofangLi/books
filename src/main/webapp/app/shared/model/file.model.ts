import { Moment } from 'moment';

export interface IFile {
  id?: number;
  userId?: number;
  fileName?: string;
  fileContent?: string;
  createTime?: Moment;
}

export class File implements IFile {
  constructor(
    public id?: number,
    public userId?: number,
    public fileName?: string,
    public fileContent?: string,
    public createTime?: Moment
  ) {}
}
