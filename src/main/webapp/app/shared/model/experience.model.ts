import { IReading } from 'app/shared/model/reading.model';

export interface IExperience {
  id?: number;
  userId?: number;
  duration?: number;
  content?: number;
  readOk?: number;
  read?: number;
  note?: number;
  readingId?: IReading;
}

export class Experience implements IExperience {
  constructor(
    public id?: number,
    public userId?: number,
    public duration?: number,
    public content?: number,
    public readOk?: number,
    public read?: number,
    public note?: number,
    public readingId?: IReading
  ) {}
}
